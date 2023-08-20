import { setCookie } from 'cookies-next';
import type { GetServerSideProps } from 'next';
import { useSession } from 'next-auth/react';
import Head from 'next/head';
import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import Header from '../components/Header';
import OGTags from '../components/OGTags';
import ReservationListElement from '../components/ReservationListElement';
import { getReservations } from '../utils/client';
import Reservation from '../utils/client/types/Reservation';
import { addHttpVisit, addMainPageVisit } from '../utils/monitoring/prometheus';
import { SharedSessionData, getSessionData } from '../utils/session/getSharedSessionData';
import Footer from '../components/Footer';

const RESERVATION_COOKIE_NAME = "i_need_reservations";

interface HomeProps extends SharedSessionData { }

export default function Home({ needsReservations: needsReservationsServerSide, idToken, isManager }: HomeProps) {

  const { status } = useSession();
  const [needsReservations, setNeedsReservations] = useState(needsReservationsServerSide);

  const iNeedReservation = (e: React.MouseEvent<HTMLButtonElement>): void => {
    e.preventDefault();
    setCookie(RESERVATION_COOKIE_NAME, "true");
    setNeedsReservations(true);
  };

  const [reservations, setReservations] = useState([] as Reservation[]);

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
        <OGTags />
      </Head>
      <Container>
        <Header idToken={idToken} isManager={isManager} activePage='home' needsReservation={needsReservations} />
      </Container>
      <Container>
        <main>
          {!needsReservations && (
            <div className="px-4 py-5 my-5 text-center">
              <img className="d-block mx-auto mb-4" src="/images/png-transparent-girl-car-dog-driving-anime-japanese-suitcase-travel-thumbnail.png" alt="Why don't you reserve some cars onii-chan" />
              <h1 className="display-5 fw-bold">You need a car</h1>
              <div className="col-lg-6 mx-auto">
                <p className="lead mb-4">People unconsciously love people with cars. <br /> Red cars are good. Big cars are better. Big red ones...</p>
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
                <p className="leam mb-4">Please log in to see reservations</p>
              </div>
            </div>
          )}
          {needsReservations && status === 'authenticated' && (
            <div className="px-4 py-1 my-1 text-center">
              <div className="row">
                <img className="col-6 col-xl-4 d-block mx-auto mb-4 img-fluid" src="/images/png-transparent-pointing-finger-hand-pointing-direction-point-people-finger-pointing-finger-you-human.png" alt="Pointing finger" />
                <div className="col-6 col-xl-8 align-middle align-items-center row">
                  <div className="mx-auto row">
                    <h1 className="display-5 fw-bold col-12">Your reservations</h1>
                    <p className="small mb-4 col-12">Just yours. No one else's.</p>
                  </div>
                </div>
              </div>
              <div className="px-4 py-5 my-5">
                <p>Reservations</p>
                <div className="col mx-auto text-start">
                  <ul className="list-group">
                    {reservations.map(r => <li key={r.id} className="list-group-item">
                      <ReservationListElement startsAt={r.startsAt} endsAt={r.endsAt} vehicleDescription={r.vehicleDescription} isCancelled={r.cancelled} />
                    </li>)}
                  </ul>
                </div>
              </div>
            </div>
          )}
        </main>
        <Footer />
      </Container>
    </>
  )
}

export const getServerSideProps: GetServerSideProps = async ({ res, req }) => {
  const sharedSessionData = await getSessionData(req);

  addHttpVisit(req.url ?? "?", req.headers["user-agent"] ?? "?");
  addMainPageVisit();

  return {
    props: { ...sharedSessionData }
  };
};
