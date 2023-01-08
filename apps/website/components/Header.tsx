import 'react';
import { useSession, signIn, signOut } from "next-auth/react";
import Link from "next/link";

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
    <div className="header">
      <Link legacyBehavior href="/">
        <a className="logo">AppLogo</a>
      </Link>
      {session && (
        <a href="#" onClick={handleSignout} className="btn-signin">
          SIGN OUT
        </a>
      )}
      {!session && (
        <a href="#" onClick={handleSignin} className="btn-signin">
          SIGN IN
        </a>
      )}
    </div>
  );
}
