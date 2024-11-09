import React from 'react';
import { useAuth } from '../context/AuthContext';

const Home = () => {
  const { getUser } = useAuth();  // Access the getUser function from context

  const user = getUser();  // Get the user object

  // If user data is available, display the name, else display a default message
  return (
    <div>
      {user && user.data ? (
        <h2>Welcome, {user.data.name}!</h2>  // Display the user's name
      ) : (
        <h2>Welcome, Guest!</h2>  // Fallback if no user is logged in
      )}
    </div>
  );
};

export default Home;