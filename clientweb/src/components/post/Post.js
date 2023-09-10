import "./post.css";
import { MoreVert } from '@mui/icons-material';
import { useState } from "react";


const Post = ({ post }) => {
  const [like, setLike] = useState(post.like)
  const [isLiked, setIsLiked] = useState(false)
  const likeHandler = () => {
    setLike(isLiked ? like - 1 : like + 1)
    setIsLiked(!isLiked)
  }
  function timeAgoFromUnixTimestamp(unixTimestamp) {
    const now = new Date();
    const timestampDate = new Date(unixTimestamp);
    const timeDifferenceInSeconds = Math.floor((now - timestampDate) / 1000);

    if (timeDifferenceInSeconds < 60) {
        return `${timeDifferenceInSeconds} giây trước`;
    } else if (timeDifferenceInSeconds < 3600) {
        const minutes = Math.floor(timeDifferenceInSeconds / 60);
        return `${minutes} phút trước`;
    } else if (timeDifferenceInSeconds < 86400) {
        const hours = Math.floor(timeDifferenceInSeconds / 3600);
        return `${hours} giờ trước`;
    } else {
        const days = Math.floor(timeDifferenceInSeconds / 86400);
        return `${days} ngày trước`;
    }
}
const formattedTime = timeAgoFromUnixTimestamp(post.createdDate);
  return (
    <div className="post">
      <div className="postWrapper">
        <div className="postTop">
          <div className="postTopLeft">
            <img
              className="postProfileImg"
              src={post.userId.avatar}
              alt=""
            />
            <span className="postUsername">
              {post.userId.displayName}
            </span>
            <span className="postDate">{formattedTime}</span>
          </div>
          <div className="postTopRight">
            <MoreVert />
          </div>
        </div>
        <div className="postCenter">
          <span className="postText">{post.content}</span>
          
            {post.image && (
              <img className="postImg" src={post.image} alt="" />
            )}
        </div>
        <div className="postBottom">
          <div className="postBottomLeft">
            <img className="likeIcon" src="assets/like.png" onClick={likeHandler} alt="" />
            <img className="likeIcon" src="assets/heart.png" onClick={likeHandler} alt="" />
            <span className="postLikeCounter">{post.countReaction} people like it</span>
          </div>
          <div className="postBottomRight">
            <span className="postCommentText">{post.countComment} comments</span>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Post