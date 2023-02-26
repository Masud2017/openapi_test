package org.tester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AudioClientTest {
    private ExecutorService service = Executors.newFixedThreadPool(3);
    private List<Future> workerResponseFutureList = new ArrayList<>();

    public Map<String, Long> getRoundTripStat() throws ExecutionException, InterruptedException {
        Map<String,List<Integer>> roundTripStat = new HashMap<>();
        // for 10 client
        Worker workerFor10Client2_1 = new Worker(10,2,1);
        Worker workerFor10Client5_1 = new Worker(10,5,1);
        Worker workerFor10Client10_1 = new Worker(10,10,1);

        // for 50 client
        Worker workerFor50Client2_1 = new Worker(50,2,1);
        Worker workerFor50Client5_1 = new Worker(50,5,1);
        Worker workerFor50Client10_1 = new Worker(50,10,1);

        // for 100 client
        Worker workerFor100Client2_1 = new Worker(100,2,1);
        Worker workerFor100Client5_1 = new Worker(100,5,1);
        Worker workerFor100Client10_1 = new Worker(100,10,1);

        // for 10 client service submition
        this.workerResponseFutureList.add(this.service.submit(workerFor10Client2_1));
        this.workerResponseFutureList.add(this.service.submit(workerFor10Client5_1));
        this.workerResponseFutureList.add(this.service.submit(workerFor10Client10_1));

        // for 50 client service submition
        this.workerResponseFutureList.add(this.service.submit(workerFor50Client2_1));
        this.workerResponseFutureList.add(this.service.submit(workerFor50Client5_1));
        this.workerResponseFutureList.add(this.service.submit(workerFor50Client10_1));

        // for 100 client service submition
        this.workerResponseFutureList.add(this.service.submit(workerFor100Client2_1));
        this.workerResponseFutureList.add(this.service.submit(workerFor100Client5_1));
        this.workerResponseFutureList.add(this.service.submit(workerFor100Client10_1));

        Map<String,Long> clientCountAndResponseTimeMapper = new HashMap<>();

        // for 10 client future gathering
        clientCountAndResponseTimeMapper.put("10-2_1", (Long) this.workerResponseFutureList.get(0).get());
        clientCountAndResponseTimeMapper.put("10-5_1", (Long) this.workerResponseFutureList.get(1).get());
        clientCountAndResponseTimeMapper.put("10-10_1", (Long) this.workerResponseFutureList.get(2).get());

        // for 50 client future gathering
        clientCountAndResponseTimeMapper.put("50-2_1", (Long) this.workerResponseFutureList.get(3).get());
        clientCountAndResponseTimeMapper.put("50-5_1", (Long) this.workerResponseFutureList.get(4).get());
        clientCountAndResponseTimeMapper.put("50-10_1", (Long) this.workerResponseFutureList.get(5).get());

        // for 100 client future gathering
        clientCountAndResponseTimeMapper.put("100-2_1", (Long) this.workerResponseFutureList.get(6).get());
        clientCountAndResponseTimeMapper.put("100-5_1", (Long) this.workerResponseFutureList.get(7).get());
        clientCountAndResponseTimeMapper.put("100-10_1", (Long) this.workerResponseFutureList.get(8).get());
        service.shutdown();

        return clientCountAndResponseTimeMapper;
    }


}
