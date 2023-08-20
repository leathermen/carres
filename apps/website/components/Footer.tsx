import 'react';
import GitHubButton from 'react-github-btn';

export default function Footer() {
  return <footer className='text-center'>
    <GitHubButton href="https://github.com/leathermen/carres" data-icon="octicon-star" data-size='large' data-show-count='true' aria-label="Star Cars Reservation System on GitHub">Star</GitHubButton>
    &nbsp;
    <GitHubButton href="https://github.com/leathermen/carres/issues" data-icon="octicon-issue-opened" data-size='large' data-show-count='true' aria-label="Issue Cars Reservation System on GitHub">Issue</GitHubButton>
  </footer>;
}