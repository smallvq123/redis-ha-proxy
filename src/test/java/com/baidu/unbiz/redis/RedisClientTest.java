package com.baidu.unbiz.redis;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.util.SafeEncoder;

import com.baidu.beidou.util.sidriver.bo.SiProductBiz.ProductPreviewRequest;
import com.baidu.unbiz.redis.codec.StringCodecCallback;
import com.baidu.unbiz.redis.config.RedisHAClientConfig;
import com.baidu.unbiz.redis.util.KvPair;

public class RedisClientTest {

    private RedisClient getRedisClient() {
        RedisHAClientConfig config = new RedisHAClientConfig();
        config.setCacheName("test-redis-cache");
        config.setRedisServerHost("10.44.176.34");
        config.setRedisServerPort(9000);
        // config.setRedisAuthKey("beidouRd");
        config.setTimeout(20000);

        RedisClient client = new RedisClient(config);
        return client;
    }

    @Test
    public void testPingPong() throws Exception {
        RedisClient client = getRedisClient();
        String pong = client.ping();
        assertThat(pong, is("PONG"));
    }

    @Test
    public void testSetAndGet() throws Exception {
        RedisClient client = getRedisClient();
        client.set("key", "value");
        Object value = client.get("key");
        assertThat(value.toString(), is("value"));
    }

    @Test
    public void testSetWithExpireAndGet() throws Exception {
        RedisClient client = getRedisClient();
        client.set("key", "value", 1);
        Object value = client.get("key");
        assertThat(value.toString(), is("value"));
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        value = client.get("key");
        assertThat(value, nullValue());
    }

    @Test
    public void testExistAndDel() throws Exception {
        RedisClient client = getRedisClient();
        client.set("key", "value");
        boolean exist = client.exists("key");
        assertThat(exist, is(true));
        client.delete("key");
        exist = client.exists("key");
        assertThat(exist, is(false));
    }

    @Test
    public void testAdd() throws Exception {
        RedisClient client = getRedisClient();
        client.set("key", "value");
        Object value = client.get("key");
        assertThat(value.toString(), is("value"));
        client.add("key", "new value");
        value = client.get("key");
        assertThat(value.toString(), is("value"));

        client.add("new key", "new value");
        value = client.get("new key");
        assertThat(value.toString(), is("new value"));
    }

    @Test
    public void testHputAndHget() throws Exception {
        RedisClient client = getRedisClient();
        String key = "SessionKey";
        client.delete(key);
        client.hput(key, "a", "123");
        Object value = client.hget(key, "a");
        assertThat(value.toString(), is("123"));
    }

    @Test
    public void testHdelAndHexist() throws Exception {
        RedisClient client = getRedisClient();
        String key = "SessionKey";
        client.hput(key, "a", "123");
        Object value = client.hget(key, "a");
        assertThat(value.toString(), is("123"));
        boolean exist = client.hExists(key, "a");
        assertThat(exist, is(true));
        client.hdel(key, "a");
        exist = client.hExists(key, "a");
        assertThat(exist, is(false));
    }

    @Test
    public void testHkeysAndHvaluesAndHlen() throws Exception {
        RedisClient client = getRedisClient();
        String key = "SessionKey";
        client.hput(key, "a", "123");
        client.hput(key, "b", "456");
        client.hput(key, "c", "789");
        Set<String> keys = client.hKeys(key);
        assertThat(keys.contains("a"), is(true));
        List<Object> values = client.hValues(key);
        assertThat(values.contains("456"), is(true));
        long len = client.hLen(key);
        assertThat(len, is(3L));
    }

    @Test
    public void testHgetall() throws Exception {
        RedisClient client = getRedisClient();
        String key = "SessionKey";
        client.hput(key, "a", "123");
        client.hput(key, "b", "456");
        client.hput(key, "c", "789");
        Map<String, Object> kv = client.hGetAll(key);
        assertThat(kv.size(), is(3));
        assertThat(kv.keySet().contains("a"), is(true));
        assertThat(kv.values().contains("789"), is(true));
    }

    @Test
    public void testHmsetAndHmget() throws Exception {
        RedisClient client = getRedisClient();
        String key = "SessionKey";
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("a", new Person(1, "111"));
        map.put("b", new Person(2, "222"));
        map.put("c", new Person(3, "333"));
        client.hmSet(key, map);
        List<Object> values = client.hmGet(key, new String[] { "a", "b" });
        assertThat(values.size(), is(2));
        System.out.println(values.get(0));
    }

    @Test
    public void testHmGetByStringSerializer() throws Exception {
        RedisClient client = getRedisClient();
        String key = "SessionKey";
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("a", "111");
        map.put("b", "222");
        map.put("c", "333");
        client.hmSet(key, map);
        List<String> values = client.hmGetByStringSerializer(key, new String[] { "a", "b" });
        assertThat(values.size(), is(2));
        System.out.println(values.get(0));
    }

    @Test
    public void testSAddAndSetMembersAndRem() throws Exception {
        RedisClient client = getRedisClient();
        String key = "setKey";
        client.delete(key);
        client.sAdd(key, "setV1");
        client.sAdd(key, "setV2");
        client.sAdd(key, "setV3");
        Set<String> members = client.sMembers(key);
        for (String string : members) {
            System.out.println(string);
        }
        assertThat(members.size(), is(3));
        client.sRem(key, "setV3");
        members = client.sMembers(key);
        assertThat(members.size(), is(2));
    }

    @Test
    public void testListPopPush() throws Exception {
        RedisClient client = getRedisClient();
        String key = "listKey";
        client.delete(key);
        client.lpush(key, "V1");
        client.lpush(key, "V2");
        client.lpush(key, "V3");
        client.rpush(key, "V4");
        client.rpush(key, "V5");
        // V3-V2-V1-V4-V5
        String item = (String) (client.lpop(key, String.class));
        System.out.println(item);
        assertThat(item, is("V3"));
        item = (String) (client.rpop(key, String.class));
        System.out.println(item);
        assertThat(item, is("V5"));
    }

    @Test
    public void testIncr() throws Exception {
        RedisClient client = getRedisClient();
        String key = "incrTestK";
        client.delete(key);
        Long result = client.incr(key);
        assertThat((Long) result, is(1L));
        System.out.println((Long) result);
        result = client.incr(key);
        assertThat((Long) result, is(2L));
        System.out.println((Long) result);
        result = client.incr(key);
        assertThat((Long) result, is(3L));
        System.out.println((Long) result);
    }

    @Test
    public void testIncrBy() throws Exception {
        RedisClient client = getRedisClient();
        String key = "incrTestK";
        client.delete(key);
        Long result = client.incrBy(key, 100);
        assertThat((Long) result, is(100L));
        System.out.println((Long) result);
        result = client.incrBy(key, 50);
        assertThat((Long) result, is(150L));
        System.out.println((Long) result);
        result = client.incrBy(key, 30);
        assertThat((Long) result, is(180L));
        System.out.println((Long) result);
    }

    /**
     * Test {@code setnx()}
     * 
     * @throws Exception
     *             if failed.
     */
    @Test
    public void testSetnx() throws Exception {
        RedisClient client = getRedisClient();
        String key = "setnxTestK";
        client.delete(key);
        Long result = client.setnx(key, 100, 100);
        assertThat(result, is(1L));
        Integer value = (Integer) client.get(key);
        assertThat(value, is(100));
    }

    @Test
    public void testHmsetByPb() throws Exception {
        GenericCodecCallback<Object> cb = new PbCodec();
        RedisClient client = getRedisClient();
        client.setCb(cb);
        client.set("pbKey", new Object());
        ProductPreviewRequest obj = (ProductPreviewRequest) client.get("pbKey");
        System.out.println(obj);
    }

    @Test
    public void testHmsetByProtostuff() throws Exception {
        GenericCodecCallback<Person> cb = new PersonCodecCallback();
        RedisClient client = getRedisClient();
        client.setCb(cb);
        Person person = new Person(1, "你好");
        client.set("pbstuffKey", person);
        Person obj = (Person) client.get("pbstuffKey");
        System.out.println(obj);
    }

    @Test
    public void testMsetMget() throws Exception {
        GenericCodecCallback<Person> cb = new PersonCodecCallback();
        RedisClient client = getRedisClient();
        client.setCb(cb);
        String key1 = "p1";
        Person person1 = new Person(1, "你好1");
        String key2 = "p2";
        Person person2 = new Person(2, "你好2");
        String key3 = "p3";
        Person person3 = new Person(3, "你好3");

        KvPair kv1 = new KvPair(key1, person1);
        KvPair kv2 = new KvPair(key2, person2);
        KvPair kv3 = new KvPair(key3, person3);

        List<KvPair> pairs = new ArrayList<KvPair>(3);
        pairs.add(kv1);
        pairs.add(kv2);
        pairs.add(kv3);

        boolean isSucc = client.mset(pairs);
        System.out.println(isSucc);

        List<String> keys = new ArrayList<String>(3);
        keys.add(key1);
        keys.add(key2);
        keys.add(key3);

        List<Object> list = (List<Object>) (client.mget(keys.toArray(new String[] {})));
        for (Object obj : list) {
            System.out.println((Person) obj);
        }
    }

    @Test
    public void testMsetMgetBin() throws Exception {
        GenericCodecCallback<Person> cb = new PersonCodecCallback();
        RedisClient client = getRedisClient();
        client.setCb(cb);
        String key1 = "p1";
        Person person1 = new Person(1, "你好1");
        String key2 = "p2";
        Person person2 = new Person(2, "你好2");
        String key3 = "p3";
        Person person3 = new Person(3, "你好3");

        byte[][] kvs = new byte[6][];
        kvs[0] = SafeEncoder.encode(key1);
        kvs[1] = cb.encode(person1);
        kvs[2] = SafeEncoder.encode(key2);
        kvs[3] = cb.encode(person2);
        kvs[4] = SafeEncoder.encode(key3);
        kvs[5] = cb.encode(person3);

        boolean isSucc = client.mset(kvs);
        System.out.println(isSucc);

        byte[][] keys = new byte[3][];
        keys[0] = SafeEncoder.encode(key1);
        keys[1] = SafeEncoder.encode(key2);
        keys[2] = SafeEncoder.encode(key3);

        List<byte[]> list = (List<byte[]>) (client.mget(keys));
        for (byte[] obj : list) {
            System.out.println(cb.decode(obj));
        }
    }
    
    @Test
    public void testStringCallback() throws Exception {
        GenericCodecCallback<String> cb = new StringCodecCallback();
        RedisClient client = getRedisClient();
        client.setCb(cb);
        String value = (String) (client.get("3764966_143434477620787885"));
        System.out.println(value);
    }

}
