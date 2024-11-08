import Link from "next/link";

export default function Home() {
  return (
      <main className="  justify-center flex-col">
        <h1>Welcome to Yelp</h1>
          <br/>
        <Link href="/oauth2/authorization/yelp" className="bg-red-600 text-white p-4">Log in</Link>
      </main>
  );
}
