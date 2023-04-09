import { signIn, signOut } from "next-auth/react";
import 'react';

const APP_ADDRESS = process.env.NEXT_PUBLIC_APP_URL;

interface HeaderProps {
  activePage: string;
  idToken: string | null;
  isManager: boolean;
  needsReservation: boolean;
}

export default function Header({ activePage, idToken, isManager, needsReservation }: HeaderProps) {
  const handleSignin = () => {
    signIn('keycloak');
  };
  const handleSignout = (idToken: string) => {
    signOut({
      callbackUrl: '/auth/realms/carres/protocol/openid-connect/logout?id_token_hint=' + idToken + '&post_logout_redirect_uri=' + APP_ADDRESS,
    });
  };

  return (
    <header className="d-flex justify-content-center py-3">
      <ul className="nav nav-pills">
        <li className="nav-item"><a href="/" className={`nav-link ${activePage === 'home' ? 'active' : ''}`} aria-current="page">Home</a></li>
        {needsReservation && (
          <li className="nav-item"><a href="/create-reservation" className={`nav-link ${activePage === 'create-reservation' ? 'active' : ''}`}>Reserve a car</a></li>
        )}
        {isManager && (
          <li className="nav-item"><a href="#" className={`nav-link ${activePage === 'dashboard' ? 'active' : ''}`}>Dashboard</a></li>
        )}
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