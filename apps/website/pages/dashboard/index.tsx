import Head from 'next/head';
import 'react';
import { Button, ButtonGroup, Container } from 'react-bootstrap';
import Header from '../../components/Header';
import { SharedSessionData, getSessionData } from '../../utils/session/getSharedSessionData';
import { GetServerSideProps } from 'next';
import AdminReservationListElement from '../../components/AdminReservationListElement';
import Reservation from '../../utils/client/types/Reservation';
import { useEffect, useState } from 'react';
import { getUsersReservations, cancelReservation as cancelReservationApi } from '../../utils/client/apiClient';
import AdminUserEmailForm from '../../components/AdminUserEmailForm';
import OGTags from '../../components/OGTags';
import { addHttpVisit } from '../../utils/monitoring/prometheus';

interface DashboardProps extends SharedSessionData { }

export default function Dashboard({ needsReservations, idToken, isManager }: DashboardProps) {
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [userId, setUserId] = useState<null | string>(null);
  const [reservations, setReservations] = useState([] as Reservation[]);

  useEffect(() => {
    if (userId === null) {
      return;
    }
    fetchReservations(userId, currentPage).then(setReservations);
  }, [userId, currentPage]);

  const fetchReservations = async (id: string, page: number): Promise<Reservation[]> => {
    const list = await getUsersReservations(id, page);
    setTotalPages(list.totalPages);
    return list.items;
  }

  const cancelReservation = async (id: string): Promise<void> => {
    await cancelReservationApi(id);
    setReservations(reservations.map(r => {
      if (r.id === id) {
        r.cancelled = true;
      }

      return r;
    }));
  }

  return (
    <>
      <Head>
        <title>Cars Reservation</title>
        <link rel="icon" href="/favicon.ico" />
        <OGTags />
      </Head>
      <Container>
        <Header idToken={idToken} isManager={isManager} activePage='dashboard' needsReservation={needsReservations} />
      </Container>
      <Container>
        {!isManager && (
          <main>
            <div className="px-4 py-1 my-1 text-center">
              <div className="row">
                <img className="col-6 col-xl-4 d-block mx-auto mb-4 img-fluid" src="/images/vibe-check.gif" alt="Secure page guardian" />
                <div className="col-6 col-xl-8 align-middle align-items-center row">
                  <div className="mx-auto row">
                    <h1 className="display-5 fw-bold col-12">Access restricted</h1>
                    <p className="small mb-4 col-12">Unfortunately, you appear to be unauthorized to access this page.</p>
                  </div>
                </div>
              </div>
            </div>
          </main>
        )}
        {!!isManager && (
          <main>
            <div className="px-4 py-1 my-1 text-center">
              <div className="row">
                <img className="col-6 col-xl-4 d-block mx-auto mb-4 img-fluid" src="/images/61U7W-2c1RL._AC_UF350,350_QL50_.jpg" alt="The throne of a ruler" />
                <div className="col-6 col-xl-8 align-middle align-items-center row">
                  <div className="mx-auto row">
                    <h1 className="display-5 fw-bold col-12">Access granted</h1>
                    <p className="small mb-4 col-12">Please sit and ponder on what to change next.</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="mt-3 px-4 py-1">
              <div className="col-12">
                <div className="row">
                  <div className="col-8 col-xl-6">
                    <AdminUserEmailForm onSubmit={async (id: string) => {
                      setUserId(id);
                      if (id !== userId) {
                        setCurrentPage(0);
                      }
                    }} />
                  </div>
                </div>
              </div>
              <div className='col mt-3 mx-auto'>
                <ul className="list-group">
                  {
                    reservations.map(r => <li key={r.id} className="list-group-item">
                      <AdminReservationListElement id={r.id} startsAt={r.startsAt} endsAt={r.endsAt} vehicleDescription={r.vehicleDescription} isCancelled={r.cancelled} onCancel={cancelReservation} />
                    </li>)
                  }
                </ul>
              </div>
              <div className="col-12 mt-1">
                <ButtonGroup aria-label="Pages navigation">
                  {getButtonsForAllPages(totalPages, currentPage, async (page: number) => {
                    setCurrentPage(page);
                    setReservations(await fetchReservations(userId ?? "", page));
                  })}
                </ButtonGroup>
              </div>
            </div>
          </main>
        )}
      </Container>
    </>
  );
}

const getButtonsForAllPages = (totalPages: number, currentPage: number, onClick: (page: number) => void) => {
  const output = [];
  for (let i = 0; i < totalPages; i++) {
    output.push(<Button key={i} onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
      if (i === currentPage) {
        return;
      }
      e.preventDefault();
      onClick(i);
    }} disabled={i === currentPage} variant={i === currentPage ? "info" : "secondary"}>{i + 1}</Button>)
  }

  return output;
}

export const getServerSideProps: GetServerSideProps = async ({ res, req }) => {
  const sharedSessionData = await getSessionData(req);

  addHttpVisit(req.url ?? "?", req.headers["user-agent"] ?? "?");

  return {
    props: { ...sharedSessionData }
  };
};