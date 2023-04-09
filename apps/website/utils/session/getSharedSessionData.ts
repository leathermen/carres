import { hasCookie } from "cookies-next";
import { IncomingMessage } from "http";
import { Session } from "next-auth";
import { getSession } from "next-auth/react";

const RESERVATION_COOKIE_NAME = "i_need_reservations";

const getSharedSessionData = async (req: IncomingMessage): Promise<SharedSessionData> => {
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
  const needsReservations = hasCookie(RESERVATION_COOKIE_NAME, { req });

  return {
    isManager,
    idToken,
    needsReservations,
  };
};

interface SharedSessionData {
  needsReservations: boolean;
  isManager: boolean;
  idToken: string | null;
}

export { getSharedSessionData as getSessionData, type SharedSessionData };
