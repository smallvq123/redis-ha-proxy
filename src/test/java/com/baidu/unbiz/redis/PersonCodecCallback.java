package com.baidu.unbiz.redis;

/**
 * 
 * ClassName: PersonCodecCallback <br/>
 * Function: 用runitme protostuff序列化person
 *
 * @author zhangxu04
 */
public class PersonCodecCallback implements GenericCodecCallback<Person> {

    ProtostuffCodec codec = new ProtostuffCodec();

    @Override
    public byte[] encode(Person obj) {
        try {
            return codec.encode(Person.class, obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Person decode(byte[] bytes) {
        try {
            return codec.decode(Person.class, bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
