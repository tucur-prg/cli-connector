package com.ns.connector;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Bucket;

import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.BucketSettings;

import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.LegacyDocument;
import com.couchbase.client.java.document.json.JsonObject;

import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewRow;

import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryRow;

import static java.lang.System.out;

public class CouchbaseClient {
    private static Logger logger = LoggerFactory.getLogger(CouchbaseClient.class);

    private CouchbaseEnvironment env;
    private Cluster cluster;
    private ClusterManager manager;
    private Bucket bucket;

    private String hostname;

    private boolean isConnection = false;

    public void open(String host) {
        hostname = host;
        env = DefaultCouchbaseEnvironment.builder()
                .connectTimeout(TimeUnit.SECONDS.toMillis(10))
                .requestBufferSize(1024)
                .build();

        connect();
    }

    protected void connect() {
        List<String> nodes = Arrays.asList(hostname.split(","));
        logger.info("connection nodes: {}", nodes);

        cluster = CouchbaseCluster.create(env, nodes);

        isConnection = true;
    }

    public void close() {
        if (isConnection) {
            if (bucket != null) {
                bucket.close();
                bucket = null;
            }
            if (cluster != null) {
                cluster.disconnect();
                cluster = null;
            }

            isConnection = false;
        }
    }

    public void login(String username, String password) {
        manager = cluster.clusterManager(username, password);
    }

    public void openBucket(String name, String password) {
        if (bucket != null) {
            bucket.close();
            bucket = null;
        }

        bucket = cluster.openBucket(name, password);
    }

    public void set(String key, String value) {
        // PHP 5.2.17 で利用する php-ext-couchbase 1.1.2 のバージョンだとLegacyDocumentでないと
        // flagsの構造の違いでデータの取得ができない
        LegacyDocument doc = LegacyDocument.create(key, value);

        out.println(bucket.upsert(doc));
    }

    public void get(String key) {
        out.println(bucket.get(key));
    }

    public void delete(String key) {
        bucket.remove(key);
    }

    public void query(String design, String view) {
        Iterator<ViewRow> iter = bucket.query(ViewQuery.from(design, view).limit(10)).rows();
        while (iter.hasNext()) {
            out.println(iter.next());
        }
    }

    public void sql(String sql) {
      Iterator<N1qlQueryRow> iter = bucket.query(N1qlQuery.simple(sql)).rows();
      while (iter.hasNext()) {
          out.println(iter.next());
      }
    }

    public void flush() {
        long start = System.currentTimeMillis();
        bucket.bucketManager().flush();
        long time = System.currentTimeMillis() - start;

        out.println("bucket flush (" + String.valueOf(time) + " msec).");
    }

    public void bucket() {
        out.println(bucket.bucketManager().info().raw());
    }

    public String bucketName() {
        if (bucket == null) {
            return null;
        }

        return bucket.name();
    }

    public void bucketList() {
        List<BucketSettings> settings = manager.getBuckets();
        Iterator<BucketSettings> iter = settings.iterator();

        out.println("| Name | Type | Password | Port | Quota | Replicas | Flush |");

        while (iter.hasNext()) {
            BucketSettings setting = iter.next();

            out.println(
                String.format(
                    "| %s | %s | %s | %d | %d | %d | %s |",
                    setting.name(),
                    setting.type(),
                    setting.password(),
                    setting.port(),
                    setting.quota(),
                    setting.replicas(),
                    setting.enableFlush() ? "TRUE" : "FALSE"
                )
            );
        }
    }

    public void info() {
        out.println(manager.info().raw());
    }
}
