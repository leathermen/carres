import axios from "axios";
import { Session } from "next-auth";
import { getSession, signIn } from "next-auth/react";
import { Token } from "../session/Token";
import CarsResponse from "./types/CarsResponse";
import ReservationsListResponse from "./types/ReservationsListResponse";
import Car from "./types/Car";
import CreateReservationRequest from "./types/CreateReservationRequest";
import Reservation from "./types/Reservation";
import { ApiError } from "./errors/ApiError";
import AdminReservationsListResponse from "./types/AdminReservationsListResponse";
import User from "./types/User";
import { NotFoundError } from "./errors/NotFoundError";

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

const getUserIdByEmail = async (email: string) => {
  return await makeApiCall<null, User>("get", `/api/v1/dashboard/user?email=${email}`);
};

const getUsersReservations = async (id: string, page: number = 0) => {
  return await makeApiCall<null, AdminReservationsListResponse>(
    "get",
    `/api/v1/dashboard/user-reservations?userId=${id}&page=${page}`
  );
};

const cancelReservation = async (id: string) => {
  return await makeApiCall<null, null>("delete", `/api/v1/dashboard/user-reservations/${id}`);
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

      const code = error.response?.data?.code;
      const message = error.response?.data?.message;

      if (!!code && !!message) {
        switch (status) {
          case 404:
            throw new NotFoundError(message, code);
          default:
            throw new ApiError(message, code);
        }
      }

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
        signIn("keycloak");
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

export {
  getReservations,
  getCars,
  getCar,
  createReservation,
  cancelReservation,
  getUserIdByEmail,
  getUsersReservations,
};
