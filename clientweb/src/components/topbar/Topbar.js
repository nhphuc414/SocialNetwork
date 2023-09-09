import "./topbar.css";
import { useContext } from "react";
import { Search, Person, Chat, Notifications,Logout  } from '@mui/icons-material';
import { MyUserContext } from "../../App";
import { Link } from "react-router-dom";
import { Button} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
const Topbar = () => {
  const nav = useNavigate();
  const [user, dispatch] = useContext(MyUserContext);
  const logout = () => {
    dispatch({
        "type": "logout"
    })
    nav("/login");
}
  return (
    <div className="topbarContainer">
      <div className="topbarLeft">
        <span className="logo">Social Network</span>
      </div>
      <div className="topbarCenter">
        <div className="searchbar">
          <Search className="searchIcon" />
          <input
            placeholder="Search for friend, post or video"
            className="searchInput"
          />
        </div>
      </div>
      <div className="topbarRight">
        <div className="topbarLinks">
          <span className="topbarLink">Homepage</span>
          <span className="topbarLink">Timeline</span>
        </div>
        <div className="topbarIcons">
          <div className="topbarIconItem">
            <Person />
          </div>
          <div className="topbarIconItem">
            <Chat />
          </div>
          <div className="topbarIconItem">
            <Notifications />
          </div>
          <div className="topbarIconItem">
          <Link to="/profile">
          <img src={user.avatar} alt="" className="topbarImg"/>
          </Link>
          </div>
        </div>
        <div>
        <Button variant="secondary" onClick={logout}><Logout/></Button>
        </div>
      </div>
    </div>
  );
}
export default Topbar