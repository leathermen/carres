import { GetServerSideProps } from 'next';
import Head from 'next/head';
import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import Header from '../../components/Header';
import { getCar } from '../../utils/client/apiClient';
import Car from '../../utils/client/types/Car';
import { SharedSessionData, getSessionData } from '../../utils/session/getSharedSessionData';
import CarReservationForm from './CarReservationForm';

interface CarReservationPageProps extends SharedSessionData { }

export default function CarReservationPage({ idToken, isManager, needsReservations }: CarReservationPageProps) {
  const [car, setCar] = useState(null as Car | null | false);

  const router = useRouter();
  useEffect(() => {
    if (car == null) {
      getCar(router.query["carId"] as string).then(car => setCar(car)).catch(err => setCar(false));
    }
  }, [car]);

  return <>
    <Head>
      <title>Cars Reservation</title>
      <link rel="icon" href="/favicon.ico" />
    </Head>
    <Container>
      <Header idToken={idToken} isManager={isManager} activePage='create-reservation' needsReservation={needsReservations} />
    </Container>
    <Container>
      <main>
        <div className="px-4 py-1 my-1 text-center">
          {!!car && (
            <div className="row">
              <img className="col-6 col-xl-4 d-block mx-auto mb-4 img-fluid" src={`/images/cars/${car.id}.png`} alt="Repeat after me: this is literally what you have been looking for" />
              <div className="col-6 col-xl-8 align-middle align-items-center row">
                <div className="mx-auto row">
                  <h1 className="display-5 fw-bold col-12">{car.manufacturer} {car.model}</h1>
                  <p className="small mb-4 col-12">Manufactured at {getYearFromDateString(car.manufacturedAt)}</p>
                </div>
              </div>
            </div>
          )}
          {car === false && (
            <div className="row">
              <img className="col-6 col-xl-4 d-block mx-auto mb-4 img-fluid" src="/images/fzuab.jpg" alt="This man looks surprised too" />
              <div className="col-6 col-xl-8 align-middle align-items-center row">
                <div className="mx-auto row">
                  <h1 className="display-5 fw-bold col-12">Not every car is here yet</h1>
                  <p className="small mb-4 col-12">Promise to fix it soon (not really)</p>
                </div>
              </div>
            </div>
          )}
          {!!car && (
            <div className="px-4 py-5 my-5">
              <div className="col-lg-6 mx-auto">
                <p className="leam mb-4 text-center">Enter the reservation details</p>
                <CarReservationForm vehicleId={car.id} onSuccess={() => setTimeout(() => router.push("/"), 1500)} />
              </div>
            </div>
          )}
        </div>
      </main>
    </Container>
  </>;
}

const getYearFromDateString = (dateString: string): string => {
  const date = new Date(dateString);
  return date.getUTCFullYear().toString();
}

export const getServerSideProps: GetServerSideProps = async ({ res, req, params }) => {
  const sharedSessionData = await getSessionData(req);

  return {
    props: {
      ...sharedSessionData
    }
  };
};