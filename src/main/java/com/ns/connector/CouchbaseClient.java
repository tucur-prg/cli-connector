package com.ns.connector;

import java.util.List;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Bucket;

import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.BucketSettings;

import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewRow;

import static java.lang.System.out;

public class CouchbaseClient {
    private CouchbaseEnvironment env;
    private Cluster cluster;
    private ClusterManager manager;
    private Bucket bucket;

    private String hostname;

    private String username;
    private String password;

    public void open(String host) {
        Logger.getLogger("com.couchbase.client").setLevel(Level.OFF);

        hostname = host;
        env = DefaultCouchbaseEnvironment.builder()
                .connectTimeout(TimeUnit.SECONDS.toMillis(10))
                .requestBufferSize(1024)
                .build();

        connect();
    }

    protected void connect() {
        cluster = CouchbaseCluster.create(env, hostname);
        if (username != null && password != null) {
            manager = cluster.clusterManager(username, password);
        }
    }

    public void close() {
        if (bucket != null) {
            bucket.close();
        }
        if (cluster != null) {
            cluster.disconnect();
        }
    }

    public void settings(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void openBucket(String name, String password) {
        bucket = cluster.openBucket(name, password);
    }

    public void set(String key, String value) {
        JsonObject content = JsonObject.fromJson(value);
        JsonDocument doc = JsonDocument.create(key, content);

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
