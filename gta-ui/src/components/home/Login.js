import React, { useState } from 'react'
import { NavLink, Navigate } from 'react-router-dom'
import { Button, Form, Grid, Segment, Message } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import { gtaApi } from '../misc/GtaApi'
import { parseJwt, handleLogError } from '../misc/Helpers'

function Login() {
  const Auth = useAuth()
  const isLoggedIn = Auth.userIsAuthenticated()
  const { getUser } = useAuth()

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [isError, setIsError] = useState(false)

  const handleInputChange = (e, { name, value }) => {
    if (name === 'username') {
      setUsername(value)
    } else if (name === 'password') {
      setPassword(value)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!(username && password)) {
      setIsError(true)
      return
    }

    try {
      const response = await gtaApi.authenticate(username, password)
      const token = response.data.accessToken;
      if (token) {
        localStorage.setItem("authToken", token);
        console.log("Token saved successfully:", token);
      } else {
        console.error("Token is undefined, check login response.");
      }
      
      const { accessToken } = response.data
      const data = parseJwt(accessToken)
      const authenticatedUser = { data, accessToken }
      

      Auth.userLogin(authenticatedUser)

      setUsername('')
      setPassword('')
      setIsError(false)
    } catch (error) {
      handleLogError(error)
      setIsError(true)
    }
  }

  const user = getUser();

  if (isLoggedIn) {

    console.log(user)

    if (user && user.data && user.data.authorities && user.data.authorities === 'ADMIN') {
      return <Navigate to="/admin" />;
    } else if (user && user.data && user.data.authorities && user.data.authorities === 'USER') {
      return <Navigate to="/home" />;
    }
    //return <Navigate to={'/home'} />
  }

  return (
    <Grid textAlign='center'>
      <Grid.Column style={{ maxWidth: 450 }}>
        <Form size='large' onSubmit={handleSubmit}>
          <Segment>
            <Form.Input
              fluid
              autoFocus
              name='username'
              icon='user'
              iconPosition='left'
              placeholder='Username'
              value={username}
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              name='password'
              icon='lock'
              iconPosition='left'
              placeholder='Password'
              type='password'
              value={password}
              onChange={handleInputChange}
            />
            <Button color='green' fluid size='large'>Login</Button>
          </Segment>
        </Form>
        <Message>{`Don't have already an account? `}
          <NavLink to="/signup" color='green' as={NavLink}>Sign Up</NavLink>
        </Message>
        {isError && <Message negative>The username or password provided are incorrect!</Message>}
      </Grid.Column>
    </Grid>
  )
}

export default Login