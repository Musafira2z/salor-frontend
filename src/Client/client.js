import { ApolloClient, HttpLink, InMemoryCache, from } from '@apollo/client';
import { onError } from "@apollo/client/link/error";
import { GRAPH_URL } from '../api/GRAPH_URL/GRAPH_URL';


const errorLink = onError(({ graphQLErrors, networkError }) => {

  if (graphQLErrors)

    graphQLErrors.forEach(({ message, locations, path }) =>

      console.log(

        `[GraphQL error]: Message: ${message}, Location: ${locations}, Path: ${path}`
      )

    );

  if (networkError) console.log(`[Network error]: ${networkError}`);

});

const link = from([
  errorLink,
  new HttpLink({
    uri: GRAPH_URL,
  })
])

const client = new ApolloClient({
  link:link,
  cache: new InMemoryCache(),

});


export default client

