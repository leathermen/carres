import 'react';
import { useSession, signIn, signOut } from "next-auth/react";
import { Session, User } from 'next-auth';

export default function Header() {
  const handleSignin = () => {
    signIn('keycloak');
  };
  const handleSignout = (idToken: string) => {
    signOut({
      callbackUrl: '/auth/realms/carres/protocol/openid-connect/logout?id_token_hint=' + idToken + '&post_logout_redirect_uri=http://localhost:3001',
    });
  };

  const { data: session } = useSession() as {
    data: Session & {
      tokenSet?: {
        accessToken: string;
        accessTokenExpires: number;
        refreshToken: string;
        idToken: string;
      }
    }
  };

  const idToken = session?.tokenSet?.idToken;

  return (
    <header className="d-flex justify-content-center py-3">
      <ul className="nav nav-pills">
        <li className="nav-item"><a href="#" className="nav-link active" aria-current="page">Home</a></li>
        <li className="nav-item"><a href="#" className="nav-link">My Reservations</a></li>
        <li className="nav-item"><a href="#" className="nav-link">Dashboard</a></li>
        {idToken && (
          <li className="nav-item"><a href="#" onClick={() => handleSignout(idToken)} className="nav-link">Sign Out</a></li>
        )}
        {!idToken && (
          <li className="nav-item"><a href="#" onClick={() => handleSignin()} className="nav-link">Sign In</a></li>
        )}
      </ul>
    </header>
  );
}
