import { hasCookie, setCookie } from 'cookies-next';
import type { GetServerSideProps } from 'next';
import { useSession } from 'next-auth/react';
import Head from 'next/head';
import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import Header from '../components/Header';
import ReservationListElement from '../components/ReservationListElement';
import { getReservations } from '../utils/client';
import Reservation from '../utils/client/types/Reservation';

const RESERVATION_COOKIE_NAME = "i_need_reservations";

interface HomeProps {
  needsReservationsServerSide: boolean;
  reservationsServerSide: Reservation[];
}

export default function Home({ needsReservationsServerSide, reservationsServerSide }: HomeProps) {

  const { data: session, status } = useSession();
  const [needsReservations, setNeedsReservations] = useState(needsReservationsServerSide);

  const iNeedReservation = (e: React.MouseEvent<HTMLButtonElement>): void => {
    e.preventDefault();
    setCookie(RESERVATION_COOKIE_NAME, "true");
    setNeedsReservations(true);
  };

  const [reservations, setReservations] = useState(reservationsServerSide);

  useEffect(() => {
    if (status === 'authenticated' && needsReservations) {
      getReservations().then(data => setReservations(data.items));
    }
  }, [needsReservations, status]);

  return (
    <>
      <Head>
        <title>Cars Reservation</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Container>
        <Header />
      </Container>
      <Container>
        <main>
          {!needsReservations && (
            <div className="px-4 py-5 my-5 text-center">
              <img className="d-block mx-auto mb-4" src="/png-transparent-girl-car-dog-driving-anime-japanese-suitcase-travel-thumbnail.png" alt="Why don't you reserve some cars onii-chan" />
              <h1 className="display-5 fw-bold">You need a car</h1>
              <div className="col-lg-6 mx-auto">
                <p className="lead mb-4">Women unconsciously love men with cars. <br /> Red cars are good. Big cars are better. Big red ones...</p>
                <p className="small mb-4">Alright, this is stereotypical nonsense. You only need a car if you need a ride. <br /> And if you need a ride...</p>
                <div className="d-grid gap-2 d-sm-flex justify-content-sm-center">
                  <button onClick={iNeedReservation} type="button" className="btn btn-primary btn-lg px-4 gap-3">Book the goddamn car</button>
                  <a href="https://www.bike-discount.de/en/bike/bike/trekking-bike/" target="_blank" type="button" className="btn btn-outline-secondary btn-lg px-4">I'd better go, b-baka</a>
                </div>
              </div>
            </div>
          )}
          {needsReservations && status === 'unauthenticated' && (
            <div className="px-4 py-5 my-5 text-center">
              <div className="col-lg-9 mx-auto">
                <p className="leam mb-4">Please log in to see reservations ({status})</p>
              </div>
            </div>
          )}
          {needsReservations && status === 'authenticated' && (
            <div className="px-4 py-5 my-5 text-center">
              <div className="col-lg-9 mx-auto">
                <p className="leam mb-4">Reservations</p>
                <div>
                  {reservations.map(r => <ReservationListElement key={r.id} />)}
                </div>
              </div>
            </div>
          )}
        </main>
      </Container>
    </>
  )
}

export const getServerSideProps: GetServerSideProps = async ({ res, req }) => {
  let reservationsServerSide = [] as Reservation[];

  if (hasCookie('next-auth.session-token')) {
    const reservations = await getReservations(true);
    reservationsServerSide = reservations.items;
  }

  const needsReservationsServerSide = hasCookie(RESERVATION_COOKIE_NAME, { req, res })
  return {
    props: { needsReservationsServerSide, reservationsServerSide }
  };
};
