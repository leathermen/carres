import { error, type RequestEvent } from '@sveltejs/kit';
import type { PageData } from '../$types';

export function load(request: RequestEvent): PageData {
  if (request.params.slug === 'entity') {
    return {
      title: 'EnTITTY',
      content: 'Welcome to our blog. Lorem ipsum dolor sit amet...'
    };
  }

  throw error(404, 'Not found');
}
