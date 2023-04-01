import { IncomingMessage } from "http";
import { Session } from "next-auth";
import { getSession } from "next-auth/react";

const getSharedSessionData = async (req: IncomingMessage) => {
  const session = (await getSession({ req })) as
    | (Session & {
        tokenSet?: {
          accessToken: string;
          accessTokenExpires: number;
          refreshToken: string;
          idToken: string;
        };
        roles?: string[];
      })
    | null;

  const isManager = session?.roles?.includes("manager") || false;
  const idToken = session?.tokenSet?.idToken || null;

  return {
    isManager,
    idToken,
  };
};

export { getSharedSessionData as getSessionData };
