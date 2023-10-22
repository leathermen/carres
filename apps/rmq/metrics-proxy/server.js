const http = require("http");
const verification = require("./verify-token");

const server = http.createServer(async (req, res) => {
  const accessTokenBearer = req.headers["authorization"];

  if (!accessTokenBearer) {
    res.writeHead(401);
    return res.end();
  }

  const accessToken = accessTokenBearer.slice("Bearer ".length);

  if (!(await verification.verifyToken(accessToken, "metrics-scraper"))) {
    res.writeHead(401);
    return res.end();
  }

  const rmqMetricsEp = `${process.env.RMQ_ADDRESS}/metrics`;

  http.get(rmqMetricsEp, (rres) => {
    res.setHeader("Content-type", rres.headers["content-type"]);
    res.writeHead(rres.statusCode);
    rres.on("data", res.write.bind(res));
    rres.on("end", res.end.bind(res));
  });
});

console.log("starting on 8080...");
server.listen(8080);
