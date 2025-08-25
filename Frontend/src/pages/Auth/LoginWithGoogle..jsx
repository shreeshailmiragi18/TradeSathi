import { Button } from '@/components/ui/button';
import { useState } from 'react';

const LoginWithGoogle = () => {
  const [error, setError] = useState(null);

  const handleGoogleLogin = async () => {
    try {
      const response = await fetch('http://localhost:5000/login/google');
      const data = await response.json();
      // Check if token exists in response
      if (data.token) {
        // Store JWT token in local storage
        // localStorage.setItem('token', data.token);
        // Redirect appuser to dashboard or any other protected route
        // Example: history.push('/dashboard');
        console.log("Redirect appuser to dashboard",data)
      } else {
        setError('Authentication failed');
      }
    } catch (error) {
      setError('Error logging in with Google');
    }
  };

  return (
    <div className='flex h-screen justify-center items-center flex-col space-y-10'>
      <h2>Login</h2>
      <Button onClick={handleGoogleLogin}>Login with Google</Button>
      {error && <p>{error}</p>}
    </div>
  );
};

export default LoginWithGoogle;
