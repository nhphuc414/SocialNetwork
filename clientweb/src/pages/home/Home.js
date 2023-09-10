import Topbar from "../../components/topbar/Topbar";
import Sidebar from "../../components/sidebar/Sidebar";
import Feed from "../../components/feed/Feed";
import Rightbar from "../../components/rightbar/Rightbar";
import "./home.css"
import { Navigate } from "react-router-dom";
import { MyUserContext } from "../../App";
import { useContext } from "react";

const Home = () => {
  const [user] = useContext(MyUserContext);
  if (user === null) {
    let next = "/login";
    return <Navigate to={next} />
  }
  return (
    <>
      <Topbar />
      <div className="homeContainer">
        <Sidebar />
        <Feed />
        <Rightbar />
      </div>
    </>
  );
}
export default Home;