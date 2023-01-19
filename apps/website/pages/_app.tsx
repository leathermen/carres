import { SessionProvider } from "next-auth/react"
import type { AppProps } from 'next/app'
import 'bootstrap/dist/css/bootstrap.min.css';
import { ThemeProvider } from "react-bootstrap";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <SessionProvider session={pageProps.session}>
      <ThemeProvider>
        <Component {...pageProps} />
      </ThemeProvider>;
    </SessionProvider>
  )
}

export default MyApp;