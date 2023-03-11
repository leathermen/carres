import axios from "axios";
import { Session } from "next-auth";
import { getSession } from "next-auth/react";
import { Token } from "../session/Token";
import CarsResponse from "./types/CarsResponse";
import { RefreshTokenExpiredError } from "./types/RefreshTokenExpiredError";
import ReservationResponse from "./types/ReservationResponse";

let token: Token | null = null;

const getReservations = async () => {
  return await makeApiCall<null, ReservationResponse>("get", "/api/v1/reservations");
};

const getCars = async () => {
  return await makeApiCall<null, CarsResponse>("get", "/api/v1/cars/available");
};

const reloadSession = () => {
  const event = new Event("visibilitychange");
  document.dispatchEvent(event);
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

  let attempts = 0;

  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      const status = error.response ? error.response.status : null;

      const session = (await getSession()) as
        | (Session & {
            tokenRefreshError?: string;
          })
        | null;

      if (status === 401 && attempts < 1) {
        attempts++;
        reloadSession();
        return instance.request(error.config);
      }

      if (status === 401 && attempts >= 1) {
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

export { getReservations, getCars };
