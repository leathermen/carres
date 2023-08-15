import { NextApiRequest, NextApiResponse } from "next";
import { register } from "prom-client";
import { verifyToken } from "../../../utils/monitoring/metricsCollectorTokenVerifier";

export default async (req: NextApiRequest, res: NextApiResponse) => {
  const accessTokenBearer = req.headers["authorization"];

  if (!accessTokenBearer) {
    res.status(401);
    return res.end();
  }

  const accessToken = accessTokenBearer.slice("Bearer ".length);

  if (!(await verifyToken(accessToken, "metrics-scraper"))) {
    res.status(401);
    return res.end();
  }

  res.setHeader("Content-type", register.contentType);
  res.send(await register.metrics());
};
