import { Counter } from "prom-client";

export default class Counters {
  public httpVisits: Counter = new Counter({
    name: "carres_website_http_requests_total",
    help: "Total number of HTTP requests",
    labelNames: ["path", "userAgent"],
  });
  public mainPageVisits: Counter = new Counter({
    name: "carres_website_main_page_visits",
    help: "Parameterless counter of main page visits",
  });
  public availableForReservationPageVisits: Counter = new Counter({
    name: "carres_website_available_for_reservation_page_visits",
    help: "List of available cars page visits",
  });
  public dashboardPageVisits: Counter = new Counter({
    name: "carres_website_dashboard_page_visits",
    help: "Dashboard page visits",
  });
  public signIns: Counter = new Counter({
    name: "carres_website_sign_ins",
    help: "Sign in actions count",
    labelNames: ["login"],
  });
  public signOuts: Counter = new Counter({
    name: "carres_website_sign_outs",
    help: "Sign outs actions count",
    labelNames: ["login"],
  });
}
