import 'react';
import { useSession, signIn, signOut } from "next-auth/react";

export default function Header() {
  const handleSignin = (e: React.MouseEvent) => {
    e.preventDefault();
    signIn('keycloak');
  };
  const handleSignout = (e: React.MouseEvent) => {
    e.preventDefault();
    signOut();
  };

  const { data: session } = useSession();

  return (
    <header className="d-flex justify-content-center py-3">
      <ul className="nav nav-pills">
        <li className="nav-item"><a href="#" className="nav-link active" aria-current="page">Home</a></li>
        <li className="nav-item"><a href="#" className="nav-link">My Reservations</a></li>
        <li className="nav-item"><a href="#" className="nav-link">Dashboard</a></li>
        {session && (
          <li className="nav-item"><a href="#" onClick={handleSignout} className="nav-link">Sign Out</a></li>
        )}
        {!session && (
          <li className="nav-item"><a href="#" onClick={handleSignin} className="nav-link">Sign In</a></li>
        )}
      </ul>
    </header>
  );
}
