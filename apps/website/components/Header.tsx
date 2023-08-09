import { signIn, signOut } from "next-auth/react";
import 'react';

interface HeaderProps {
  activePage: string;
  idToken: string | null;
  isManager: boolean;
  needsReservation: boolean;
}

export default function Header({ activePage, idToken, isManager, needsReservation }: HeaderProps) {
  const handleSignin = async () => {
    await signIn('keycloak');
  };
  const handleSignout = async () => {
    await signOut();
  };

  return (
    <header className="d-flex justify-content-center py-3">
      <ul className="nav nav-pills">
        <li className="nav-item"><a href="/" className={`nav-link ${activePage === 'home' ? 'active' : ''}`} aria-current="page">Home</a></li>
        {needsReservation && (
          <li className="nav-item"><a href="/create-reservation" className={`nav-link ${activePage === 'create-reservation' ? 'active' : ''}`}>Reserve a car</a></li>
        )}
        {isManager && (
          <li className="nav-item"><a href="/dashboard" className={`nav-link ${activePage === 'dashboard' ? 'active' : ''}`}>Dashboard</a></li>
        )}
        {idToken && (
          <li className="nav-item"><a href="#" onClick={async () => await handleSignout()} className="nav-link">Sign Out</a></li>
        )}
        {!idToken && (
          <li className="nav-item"><a href="#" onClick={async () => await handleSignin()} className="nav-link">Sign In</a></li>
        )}
      </ul>
    </header>
  );
}