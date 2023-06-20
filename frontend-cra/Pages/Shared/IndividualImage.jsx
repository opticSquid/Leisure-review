import Image from "mui-image";

function IndividualImage({ url }) {
  const baseUrl = "http://localhost:5000";
  return (
    <Image
      src={`${baseUrl + url}?w=248&fit=crop&auto=format`}
      srcSet={`${baseUrl + url}?w=248&fit=crop&auto=format&dpr=2 2x`}
      alt={url}
      loading="lazy"
    />
  );
}

export default IndividualImage;
