import { Session } from 'next-auth';
import { useSession } from 'next-auth/react';
import Head from 'next/head';
import Header from '../components/Header';

export default function Home() {
  const { data, status } = useSession();
  const loading = status === "loading"

  const session = data as Session & { user: { accessToken: string, refreshToken: string } } | null;

  console.log(session?.user.accessToken);

  return (
    <div className="container">
      <Head>
        <title>Nextjs | Next-Auth</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Header />
      <main>
        <div>
          {loading && <div>Loading...</div>}
          {
            session &&
            <>
              <h1>Welcome, {session.user?.name ?? session.user?.email ?? 'unknown'}!</h1>
              <p style={{ marginBottom: '10px' }}> </p> <br />
              <img src={session.user?.image ?? undefined} alt="" />
            </>
          }
          {
            !session &&
            <>
              <p>Please log in to continue</p>
              <img src="no-user.jpg" alt="" />
            </>
          }
        </div>
      </main>
    </div>
  )
}