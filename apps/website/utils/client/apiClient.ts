import axios from "axios";
import { Session } from "next-auth";
import { getSession } from "next-auth/react";

interface Token {
  accessToken: string;
  refreshToken: string;
  accessTokenExpires: number;
}

let token: Token | null = null;

const makeApiCall = async <TRequest, TResponse>(
  method: string,
  address: string,
  data: TRequest = {} as TRequest
): Promise<TResponse> => {
  const url = `${process.env.NEXT_PUBLIC_API_URL as string}${address}`;

  let response = await axios.request({
    method,
    url,
    data,
    headers: {
      Authorization: `Bearer ${await getActiveToken()}`,
    },
  });

  if (response.status === 401) {
    const newTokenRes = await axios.get(
      `${process.env.NEXT_PUBLIC_APP_URL}/api/auth/refresh`
    );
    const newToken = newTokenRes.data.accessToken;
    response = await axios.request({
      method,
      url,
      data,
      headers: {
        Authorization: `Bearer ${newToken}`,
      },
    });
  }

  return response.data as TResponse;
};

const getActiveToken = async () => {
  if (token === null || token.accessTokenExpires < new Date().getTime()) {
    const session = (await getSession()) as Session & {
      user: {
        accessToken: string;
        refreshToken: string;
        accessTokenExpires: number;
      };
    };

    token = session.user;
  }

  return token.accessToken;
};

export { makeApiCall };
