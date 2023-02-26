package org.tester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.Callable;

@SuppressWarnings("ALL")
public class Worker implements Callable {
    private Integer clientCount = 0;
    private Integer ratio1 = 0;
    private Integer ratio2 = 0;
    private long allTotalTime = 0;
    public Worker (Integer clientCount,Integer ratio1, Integer ratio2) {
        this.clientCount = clientCount;
        this.ratio1 = ratio1;
        this.ratio2 = ratio2;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 0; i < this.clientCount; i++) {
            try (CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setConnectTimeout(Timeout.of(Duration.ofSeconds(5000)))
                            .setConnectionRequestTimeout(Timeout.of(Duration.ofSeconds(5000)))
                            .setResponseTimeout(Timeout.of(Duration.ofSeconds(5000)))
                            .build())
                    .build()) {

                long timeTakenForPost = 0;
                for (int j = 0; j < this.ratio2;j++) {

                    AudioItem item = new AudioItem();
                    item.setArtistName("testartist");
                    item.setAlbumTitle("test album");
                    item.setId(Double.valueOf(1));
                    item.setNumberOfCopiesSold(1000);
                    item.setNumberOfReiview(100);
                    item.setYear(2023);
                    item.setTrackNumber(01);
                    item.setTrackTitle("tracktitle");

                    String json = serialize(item);
                    StringEntity stringEntity = new StringEntity(json);

                    ClassicHttpRequest httpPost = ClassicRequestBuilder.post("http://155.248.224.189:4444/api/v1/setaudioitem")
                            .setEntity(stringEntity).setHeader(HttpHeaders.CONTENT_TYPE, "application/json").setHeader(HttpHeaders.ACCEPT,"application/json")
                            .build();
                    long startTime = System.nanoTime();
                    httpclient.execute(httpPost, response -> {
                        System.out.println("Status code from post : "+response.getCode() + " " + response.getReasonPhrase());
                        final HttpEntity entity2 = response.getEntity();

                        EntityUtils.consume(entity2);
                        response.close();
                        return null;
                    });
                    long endTime = System.nanoTime();
                    timeTakenForPost = (endTime - startTime) / 1000000;
                }


                long specificTime = 0;
                for (int p1 = 0; p1 < this.ratio1 - 1; p1++) {
                    // specific data based request
                    ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://155.248.224.189:4444/api/v1/audioitemname/testartist")
                            .build();

                    long tempStartTime = System.nanoTime();
                    httpclient.execute(httpGet, response -> {
                        System.out.println("Status code from get 1 : "+response.getCode() + " " + response.getReasonPhrase());
                        final HttpEntity entity1 = response.getEntity();

                        EntityUtils.consume(entity1);
                        response.close();
                        return null;
                    });
                    long tempEndTime = System.nanoTime();

                    long totalTime = (tempEndTime - tempStartTime) / 1000000;
                    specificTime += totalTime;
                }

                long allTime = 0;

                for (int p2 = 0; p2 < 1; p2++) {
                    // fetch all data
                    ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://155.248.224.189:4444/api/v1/audioitemlist")
                            .build();

                    long tempStartTime = System.nanoTime();
                    httpclient.execute(httpGet, response -> {
//                        try {
////                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
                        System.out.println("Status code from get 2 : "+response.getCode() + " " + response.getReasonPhrase());
                        final HttpEntity entity1 = response.getEntity();
                        EntityUtils.consume(entity1);
                        response.close();
                        return null;
                    });
                    long tempEndTime = System.nanoTime();

                    long totalTime = (tempEndTime - tempStartTime) / 1000000;
                    allTime += totalTime;
                }


                this.allTotalTime += timeTakenForPost + allTime + specificTime;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return this.allTotalTime;
    }

    private static String serialize(Serializable obj) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
