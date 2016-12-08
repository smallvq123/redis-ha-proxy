package com.baidu.unbiz.redis;

import com.baidu.beidou.util.sidriver.bo.SiProductBiz;
import com.baidu.beidou.util.sidriver.bo.SiProductBiz.AdUrl;
import com.baidu.beidou.util.sidriver.bo.SiProductBiz.FlowConditionType;
import com.baidu.beidou.util.sidriver.bo.SiProductBiz.ProductPreviewRequest;
import com.baidu.beidou.util.sidriver.bo.SiProductBiz.ProductTemplateSize;
import com.baidu.beidou.util.sidriver.bo.SiProductBiz.TemplateElementConf;
import com.baidu.beidou.util.sidriver.bo.SiProductBiz.TemplateMaterial;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class PbCodec implements GenericCodecCallback<Object> {

    @Override
    public byte[] encode(Object obj) {
        // 构造请求
        ProductPreviewRequest.Head.Builder head = ProductPreviewRequest.Head.newBuilder();
        head.setReserved(1);

        ProductPreviewRequest.Builder previewReq = ProductPreviewRequest.newBuilder();
        previewReq.setHead(head.build());
        previewReq.setTemplateId(888888);
        previewReq.setUserid(123456);

        FlowConditionType.Builder flowType = FlowConditionType.newBuilder();
        flowType.setFlowTag(1);
        previewReq.setFlowCondition(flowType);
        previewReq.setDefaultIcon(ByteString.copyFromUtf8("http://baidu.com/百度"));

        previewReq.setTemplateWl(TemplateMaterial.MT_HTML);

        AdUrl.Builder adUrlBuilder = AdUrl.newBuilder();
        adUrlBuilder.setTargetUrl("http://baidu.com/targeturl");
        adUrlBuilder.setShowUrl("http://baidu.com/showurl");
        adUrlBuilder.setWirelessShowUrl("");
        adUrlBuilder.setWirelessTargetUrl("");
        previewReq.setAdUrl(adUrlBuilder);

        SiProductBiz.ProductFilterCondition.Builder filterBuilder = SiProductBiz.ProductFilterCondition.newBuilder();
        filterBuilder.setName(ByteString.copyFromUtf8("city"));
        filterBuilder.setOperation(ByteString.copyFromUtf8("like"));
        filterBuilder.setType(1);
        filterBuilder.setValue(ByteString.copyFromUtf8("北京"));
        previewReq.addFilterCondition(filterBuilder.build());

        TemplateElementConf.Builder templateElementConfBuilder = TemplateElementConf.newBuilder();
        templateElementConfBuilder.setNo(5);
        templateElementConfBuilder.setName(ByteString.copyFromUtf8("测试name下"));
        templateElementConfBuilder.setValue(ByteString.copyFromUtf8("这里是值"));
        templateElementConfBuilder.setLiteral(ByteString.copyFromUtf8("literal goes here"));
        previewReq.addTemplateConf(templateElementConfBuilder.build());

        ProductTemplateSize.Builder sizeBuilder = ProductTemplateSize.newBuilder();
        sizeBuilder.setHeight(500);
        sizeBuilder.setWidth(600);
        sizeBuilder.setType(1);
        previewReq.addTemplateSize(sizeBuilder.build());

        return previewReq.build().toByteArray();
    }

    @Override
    public Object decode(byte[] bytes) {
        try {
            return ProductPreviewRequest.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
