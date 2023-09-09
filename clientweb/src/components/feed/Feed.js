import { useContext, useEffect, useState } from "react"
import { Alert } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import LoadingSpinner from "../../layout/LoadingSpinner";
import Post from "../post/Post";
import Share from "../share/Share";
import "./feed.css";


const Feed = () => {
  const [posts, setPosts] = useState(null);
  useEffect(() => {
    const loadPosts = async () => {
      try {
        let e = endpoints['posts'];
        let res = await Apis.get(e);
        setPosts(res.data);
        console.info(res.data);
      } catch (ex) {
        console.error(ex);
      }
    }
    loadPosts();
  }, []);
  if (posts === null) 
  return (
    <div className="feed">
      <div className="feedWrapper">
        <Share />
        <div>Không có bài viết.</div></div>
    </div>
  );
return (
  <div className="feed">
    <div className="feedWrapper">
      <Share />
      {posts.map((p) => (
        <Post key={p.id} post={p} />
      ))}
    </div>
  </div>
);
}
export default Feed
