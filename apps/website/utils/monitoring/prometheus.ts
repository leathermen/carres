import { NextRequest, NextResponse } from "next/server";
import { Counter, register, collectDefaultMetrics } from "prom-client";
import Counters from "./counters";

const globalCounters = globalThis as unknown as {
  defaultMetricsCollected: true | undefined;
  counters: Counters | undefined;
};

let counters: Counters;

if (!globalCounters.defaultMetricsCollected) {
  collectDefaultMetrics();
  globalCounters.defaultMetricsCollected = true;
}
if (!globalCounters.counters) {
  globalCounters.counters = new Counters();
}
counters = globalCounters.counters;

export const addHttpVisit = (path: string, userAgent: string) =>
  counters.httpVisits.inc({
    path,
    userAgent,
  });

export const addMainPageVisit = () => counters.mainPageVisits.inc();
export const addAvailableForReservationPageVisit = () =>
  counters.availableForReservationPageVisits.inc();
export const addDashboardPageVisit = () => counters.dashboardPageVisits.inc();

export const addSignIn = (login: string) => counters.signIns.inc({ login });
export const addSignOut = (login: string) => counters.signOuts.inc({ login });
