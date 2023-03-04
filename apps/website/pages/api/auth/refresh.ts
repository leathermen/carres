import type { NextApiRequest, NextApiResponse } from "next";
import { Session } from "next-auth";
import { getSession } from "next-auth/react";
import refreshAccessToken from "../../../utils/api/refreshAccessToken";
import { Token } from "../../../utils/session/Token";

export default async (req: NextApiRequest, res: NextApiResponse) => {
  const session = (await getSession({ req })) as Session & {
    tokenSet: Token;
  };

  if (!session) {
    throw new Error("No session found!");
  }

  try {
    const newTokenData = await refreshAccessToken(session.tokenSet);
    session.tokenSet = newTokenData;
    res.status(200).json(newTokenData);
  } catch (e) {
    res.status(500).json({
      error: e,
    });
  }
};
