import "./login.css";
import { useContext, useState } from "react";
import { Form, Button } from "react-bootstrap";
import cookie from "react-cookies";
import { Navigate, useNavigate, useSearchParams, Link } from "react-router-dom";
import { MyUserContext } from "../../App";
import Apis, { authApi, endpoints } from "../../configs/Apis";

const Login = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [q] = useSearchParams();
  const login = (evt) => {
    evt.preventDefault();
    const process = async () => {
      try {
        let res = await Apis.post(endpoints['login'], {
          "username": username,
          "password": password
        });
        cookie.save("token", res.data);
        let { data } = await authApi().get(endpoints['current-user']);
        cookie.save("user", data);
        dispatch({
          "type": "login",
          "payload": data
        });
      } catch (err) {
        alert(err.response.data);
      }
    }

    process();
  }

  if (user !== null) {
    let next = q.get("next") || "/";
    return <Navigate to={next} />
  }

  return (

    <div className="login">
      <div className="loginWrapper">
        <div className="loginLeft">
          <h3 className="loginLogo">Social Network</h3>
          <span className="loginDesc">
            Connect with friends.
          </span>
        </div>
        <div className="loginRight">
            <Form onSubmit={login} className="loginBox">
              <Form.Control value={username} onChange={e => setUsername(e.target.value)} className="loginInput" type="text" placeholder="Username..." />
              <Form.Control value={password} onChange={e => setPassword(e.target.value)} className="loginInput" type="password" placeholder="Password..." />
              <Button className="loginButton" type="submit">Login</Button>
              
              <Button className="loginRegisterButton">
              <Link to="/register" className="loginRegisterButton">
                Create a New Account
                </Link>
              </Button>
            
            </Form>
        </div>
      </div>
    </div>
  );
}
export default Login
