import "./share.css";
import { PermMedia, Label, Room, EmojiEmotions, Cancel } from '@mui/icons-material'
import { MyUserContext } from "../../App";
import { useContext, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Apis, { authApi, endpoints } from "../../configs/Apis";
const Share = () => {
  const [user] = useContext(MyUserContext);
  const nav = useNavigate();
  const [err, setErr] = useState(null);
  const [content, setContent] = useState(null);
  const [loading, setLoading] = useState(false);
  const [file, setFile] = useState(null);
  const submitHandler = async (e) => {
    e.preventDefault();
    const process = async () => {
      let form = new FormData();
      form.append("content",content);
      if (file!==null)
      {
        form.append("image",file[0].file)
      }
      console.info(form.get("userId"))
      setLoading(true);
      let {data} = await authApi().post(endpoints['addpost'], form);
            if (data.status === 200) {
              setContent('');
              setFile(null)
            } else
            setErr("Hệ thống bị lỗi!");
    }
    process()
  }
  return (
    <form className="share" onSubmit={submitHandler}>
      <div className="shareWrapper">
        <div className="shareTop">
          <img className="shareProfileImg" src={user.avatar} alt="" />
          <input
            placeholder={"What's in your mind " + user.displayName + "?"}
            className="shareInput"
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <hr className="shareHr" />
        <hr className="shareHr" />
        {file && (
          <div className="shareImgContainer">
            <img className="shareImg" src={URL.createObjectURL(file)} alt="" />
            <Cancel className="shareCancelImg" onClick={() => setFile(null)} />
          </div>
        )}
        <div className="shareBottom">
          <div className="shareOptions">
            <label htmlFor="file" className="shareOption">
              <PermMedia htmlColor="tomato" className="shareIcon" />
              <span className="shareOptionText">Photo or Video</span>
              <input
                style={{ display: "none" }}
                type="file"
                id="file"
                accept=".png,.jpeg,.jpg"
                onChange={(e) => setFile(e.target.files[0])}
              />
            </label>
          </div>
          <button className="shareButton" type="submit">Share</button>
        </div>
      </div>
    </form>
  );
}
export default Share