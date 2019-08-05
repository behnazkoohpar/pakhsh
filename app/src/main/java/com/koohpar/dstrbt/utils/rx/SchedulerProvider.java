package com.koohpar.dstrbt.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by shb on 10/29/2017.
 */

public interface SchedulerProvider  {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
