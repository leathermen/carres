import { GetServerSideProps } from 'next';
import { signIn, useSession } from 'next-auth/react';
import Head from 'next/head';
import 'react';
import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import CarListElement from '../components/CarListElement';
import Header from '../components/Header';
import { getCars } from '../utils/client/apiClient';
import Car from '../utils/client/types/Car';
import { RefreshTokenExpiredError } from '../utils/client/types/RefreshTokenExpiredError';
import { getSessionData } from '../utils/session/getSharedSessionData';

interface NewReservationScreenProps {
  isManager: boolean;
  idToken: string;
}

export default function NewReservationScreen({ isManager, idToken }: NewReservationScreenProps) {

  const { data: session, status } = useSession();
  const [cars, setCars] = useState([] as Car[]);


  useEffect(() => {
    if (status !== 'authenticated') return;

    getCars().then(cars => setCars(cars.items)).catch(error => {
      if (error instanceof RefreshTokenExpiredError) {
        signIn('keycloak');
      }
    });
  }, [status]);

  return (
    <>
      <Head>
        <title>Cars Reservation</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Container>
        <Header isManager={isManager} idToken={idToken} activePage='create-reservation' />
      </Container>
      <Container>
        <main>
          <div className="px-4 py-1 my-1 text-center">
            <div className="row">
              <img className="col-6 col-xl-4 d-block mx-auto mb-4 img-fluid" src="/pngtree-car-seller-deals-buying-and-sale-flat-illustration-png-image_5426832.jpg" alt="Just look how important you are when you have a car" />
              <div className="col-6 col-xl-8 align-middle align-items-center row">
                <div className="mx-auto row">
                  <h1 className="display-5 fw-bold col-12">Just one step away</h1>
                  <p className="small mb-4 col-12">Okay, now it's time to focus a little and pick the right one.</p>
                </div>
              </div>
            </div>
          </div>
          <div className="mt-0">
            <div className="row">
              {idToken && (
                <>
                  <p className="col-12 leam mb-4 text-center">Available cars</p>
                  <div className='col-9 mx-auto'>
                    {cars.map(c => <CarListElement key={c.id} id={c.id} manufacturer={c.manufacturer} model={c.model} manufacturedAt={c.manufacturedAt} />)}
                  </div>
                </>
              )}
              {!idToken && (
                <p className="col-12 leam mb-4 text-center">Please log in to see available cars</p>
              )}
            </div>
          </div>
        </main>
      </Container>
    </>
  );
}

export const getServerSideProps: GetServerSideProps = async ({ res, req }) => {
  const sharedSessionData = await getSessionData(req);

  return {
    props: { ...sharedSessionData }
  };
};
