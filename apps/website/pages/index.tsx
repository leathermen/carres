import { Session } from 'next-auth';
import { useSession } from 'next-auth/react';
import Head from 'next/head';
import Header from '../components/Header';
import styles from '../styles/Home.module.css';

export default function Home() {
  const { data, status } = useSession();
  const loading = status === "loading"

  const session = data as Session & { user: { accessToken: string } } | null;

  console.log(session?.user.accessToken);

  return (
    <div className={styles.container}>
      <Head>
        <title>Nextjs | Next-Auth</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Header />
      <main className={styles.main}>
        <div className={styles.user}>
          {loading && <div className={styles.title}>Loading...</div>}
          {
            session &&
            <>
              <h1 className={styles.title}>Welcome, {session.user?.name ?? session.user?.email ?? 'unknown'}!</h1>
              <code>
                {JSON.stringify(session, null, 4)}
              </code>
              <p style={{ marginBottom: '10px' }}> </p> <br />
              <img src={session.user?.image ?? undefined} alt="" className={styles.avatar} />
            </>
          }
          {
            !session &&
            <>
              <p className={styles.title}>Please log in to continue</p>
              <img src="no-user.jpg" alt="" className={styles.avatar} />
            </>
          }
        </div>
      </main>
    </div>
  )
}