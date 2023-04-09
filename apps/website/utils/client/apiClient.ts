import axios from "axios";
import { Session } from "next-auth";
import { getSession } from "next-auth/react";
import { Token } from "../session/Token";
import CarsResponse from "./types/CarsResponse";
import { RefreshTokenExpiredError } from "./types/RefreshTokenExpiredError";
import ReservationsListResponse from "./types/ReservationsListResponse";
import Car from "./types/Car";
import CreateReservationRequest from "./types/CreateReservationRequest";
import Reservation from "./types/Reservation";

let token: Token | null = null;

const getReservations = async () => {
  return await makeApiCall<null, ReservationsListResponse>("get", "/api/v1/reservations");
};

const createReservation = async (request: CreateReservationRequest) => {
  return await makeApiCall<CreateReservationRequest, Reservation>(
    "post",
    "/api/v1/reservations",
    request
  );
};

const getCars = async () => {
  return await makeApiCall<null, CarsResponse>("get", "/api/v1/cars/available");
};

const getCar = async (carId: string) => {
  return await makeApiCall<null, Car>("get", `/api/v1/cars/${carId}`);
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

export { getReservations, getCars, getCar, createReservation };
