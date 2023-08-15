import { NextRequest, NextResponse } from "next/server";
import { Counter, register, collectDefaultMetrics } from "prom-client";

const globalCounters = globalThis as unknown as {
  defaultMetricsCollected: true | undefined;
  httpVisits: Counter | undefined;
};

let httpVisits: Counter;

if (process.env.NODE_ENV === "production") {
  collectDefaultMetrics();
  httpVisits = new Counter({
    name: "http_requests_total",
    help: "Total number of HTTP requests",
    labelNames: ["path", "userAgent"],
  });
} else {
  if (!globalCounters.defaultMetricsCollected) {
    collectDefaultMetrics();
    globalCounters.defaultMetricsCollected = true;
  }
  if (!globalCounters.httpVisits) {
    globalCounters.httpVisits = new Counter({
      name: "http_requests_total",
      help: "Total number of HTTP requests",
      labelNames: ["path", "userAgent"],
    });
  }
  httpVisits = globalCounters.httpVisits;
}

export const addHttpVisit = (path: string, userAgent: string) =>
  httpVisits.inc({
    path,
    userAgent,
  });
