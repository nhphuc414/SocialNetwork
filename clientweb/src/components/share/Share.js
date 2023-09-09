import "./share.css";
import {PermMedia, Label,Room, EmojiEmotions} from '@mui/icons-material'
import { MyUserContext } from "../../App";
import { useContext } from "react";
const Share = () => {
  const [user] = useContext(MyUserContext);
  return (
    
    <div className="share">
      <div className="shareWrapper">
        <div className="shareTop">
          <img className="shareProfileImg" src={user.avatar} alt="" />
          <input
            placeholder="What's in your mind Safak?"
            className="shareInput"
          />
        </div>
        <hr className="shareHr"/>
        <div className="shareBottom">
            <div className="shareOptions">
                <div className="shareOption">
                    <PermMedia htmlColor="tomato" className="shareIcon"/>
                    <span className="shareOptionText">Photo or Video</span>
                </div>
            </div>
            <button className="shareButton">Share</button>
        </div>
      </div>
    </div>
  );
}
export default Share