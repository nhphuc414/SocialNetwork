import "./register.css";
import { Link } from 'react-router-dom';
import { useRef, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { endpoints } from "../../configs/Apis";
import LoadingSpinner from "../../layout/LoadingSpinner";
const Register = () => {
  const [user, setUser] = useState({
    "username": "",
    "password": "",
    "displayName": "",
    "email": "",
    "identity": "",
    "confirmPass": ""
  });
  const [err, setErr] = useState(null);
  const [loading, setLoading] = useState(false);
  const avatar = useRef();
  const background = useRef();
  const nav = useNavigate();

  const register = (evt) => {
    evt.preventDefault();
    const process = async () => {
      let form = new FormData();
      for (let field in user)
        if (field !== "confirmPass")
          form.append(field, user[field]);
      form.append("avatar", avatar.current.files[0]);
      form.append("background", background.current.files[0]);
      setLoading(true)
      console.info(form);
      let res = await Apis.post(endpoints['register'], form);
      if (res.status === 201) {
        nav("/login");
      } else
        setErr("Hệ thống bị lỗi!");
    }

    if (user.password === user.confirmPass)
      process();
    else {
      setErr("Mật khẩu KHÔNG khớp!");
    }
  }

  const change = (evt, field) => {
    // setUser({...user, [field]: evt.target.value})
    setUser(current => {
      return { ...current, [field]: evt.target.value }
    })
  }
  return (
    <>
      {err === null ? "" : <Alert variant="danger">{err}</Alert>}
      <div className="login">
        <div className="loginWrapper">
          <div className="loginLeft">
            <h3 className="loginLogo">Social network</h3>
            <span className="loginDesc">
              Connect with friends and the world around you on Lamasocial.
            </span>
          </div>
          <div className="loginRight">
            <Form onSubmit={register} className="loginBox">
              <Form.Control type="text" className="loginInput" onChange={(e) => change(e, "displayName")} placeholder="Tên" required />
              <Form.Control type="email" className="loginInput" onChange={(e) => change(e, "email")} placeholder="Email" />
              <Form.Control type="identity" className="loginInput" onChange={(e) => change(e, "identity")} placeholder="Identity number" />
              <Form.Control value={user.username} className="loginInput" onChange={(e) => change(e, "username")} type="text" placeholder="Tên đăng nhập" required />
              <Form.Control value={user.password} className="loginInput" onChange={(e) => change(e, "password")} type="password" placeholder="Mật khẩu" required />
              <Form.Control value={user.confirmPass} className="loginInput" onChange={(e) => change(e, "confirmPass")} type="password" placeholder="Xác nhận mật khẩu" required />
              
              <div className="loginInput" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <label>Avatar:</label>
                <Form.Control type="file" ref={avatar} required/>
              </div>
              <div className="loginInput" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <label>Background:</label>
                <Form.Control type="file" ref={background} required/>
              </div>
              {loading === true ? <LoadingSpinner /> : <Button variant="info" className="loginButton" type="submit">Đăng ký</Button>}
              <Button className="loginRegisterButton">
                <Link to="/login" className="loginRegisterButton">
                  Log into Account
                </Link>
              </Button>
            </Form>
          </div>
        </div>
      </div>
    </>
  );
}
export default Register