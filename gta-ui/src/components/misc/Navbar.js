import React from 'react'
import { Link } from 'react-router-dom'
import { Container, Menu } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'

function Navbar() {
  const { getUser, userIsAuthenticated, userLogout } = useAuth()

  const logout = () => {
    userLogout()
  }

  const enterMenuStyle = () => {
    return userIsAuthenticated() ? { "display": "none" } : { "display": "block" }
  }

  const logoutMenuStyle = () => {
    return userIsAuthenticated() ? { "display": "block" } : { "display": "none" }
  }

  const adminPageStyle = () => {
    const user = getUser()
    return user && user.data.authorities === 'ADMIN' ? { "display": "block" } : { "display": "none" }
  }

  const userPageStyle = () => {
    const user = getUser()
    return user && user.data.authorities === 'USER' ? { "display": "block" } : { "display": "none" }
  }

  const getUserName = () => {
    const user = getUser()
    return user ? user.data.email : ''
  }

  return (
    <Menu inverted color='green' stackable size='massive' style={{ borderRadius: 0 }}>
      <Container>
        <Menu.Item header>GTA Applications</Menu.Item>
        <Menu.Item as={Link} exact='true' to="/">Home</Menu.Item>
        <Menu.Item as={Link} to="/adminpage" style={adminPageStyle()}>AdminPage</Menu.Item>
        <Menu.Item as={Link} to="/applicationpage" style={userPageStyle()}> Application Page</Menu.Item>
        <Menu.Menu position='right'>
          <Menu.Item as={Link} to="/login" style={enterMenuStyle()}>Login</Menu.Item>
          <Menu.Item as={Link} to="/signup" style={enterMenuStyle()}>Sign Up</Menu.Item>
          <Menu.Item header style={logoutMenuStyle()}>{`Hi ${getUserName()}`}</Menu.Item>
          <Menu.Item as={Link} to="/" style={logoutMenuStyle()} onClick={logout}>Logout</Menu.Item>
        </Menu.Menu>
      </Container>
    </Menu>
  )
}

export default Navbar
