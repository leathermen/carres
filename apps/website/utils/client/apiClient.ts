import axios from "axios";
import { Session } from "next-auth";
import { getSession, signIn } from "next-auth/react";
import { Token } from "../session/Token";
import { RefreshTokenExpiredError } from "./types/RefreshTokenExpiredError";
import ReservationResponse from "./types/ReservationResponse";

let token: Token | null = null;

const getReservations = async (server = false) => {
  console.log({ server });
  if (server) {
    console.log({ token: getActiveToken() });
  }
  return await makeApiCall<null, ReservationResponse>("get", "/api/v1/reservations");
};

const makeApiCall = async <TRequest, TResponse>(
  method: string,
  address: string,
  data: TRequest = {} as TRequest
): Promise<TResponse> => {
  const url = `${process.env.NEXT_PUBLIC_API_URL as string}${address}`;

  const instance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL as string,
  });

  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      const status = error.response ? error.response.status : null;

      const session = (await getSession()) as
        | (Session & {
            tokenRefreshError?: string;
          })
        | null;

      if (status === 401 && !!session?.tokenRefreshError) {
        throw new RefreshTokenExpiredError();
      }
    }
  );

  let response = await instance.request({
    method,
    url,
    data,
    headers: {
      Authorization: `Bearer ${await getActiveToken()}`,
    },
  });

  return response.data as TResponse;
};

const getActiveToken = async () => {
  if (token === null || token.accessTokenExpires < new Date().getTime()) {
    const session = (await getSession()) as Session & {
      tokenSet: Token;
    };

    token = session.tokenSet;
  }

  return token.accessToken;
};

export { getReservations };
