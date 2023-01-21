import type { NextApiRequest, NextApiResponse } from "next";
import { getSession } from "next-auth/react";
import refreshAccessToken from "../../../utils/api/refreshAccessToken";

export default async (req: NextApiRequest, res: NextApiResponse) => {
  const sess = await getSession({ req });

  if (!sess) {
    throw new Error("No session found!");
  }

  try {
    const newTokenData = await refreshAccessToken(sess.user);

    res.status(200).json(newTokenData);
  } catch (e) {
    res.status(500).json({
      error: e,
    });
  }
};
