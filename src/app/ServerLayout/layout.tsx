// src/app/ServerLayout/index.tsx

import { Geist, Geist_Mono } from "next/font/google";
import type { Metadata } from "next";
import Topbar from "@/components/ServerComponent/Topbar";  // 서버 전용 Topbar

// 폰트 설정
const geistSans = Geist({ variable: "--font-geist-sans", subsets: ["latin"] });
const geistMono = Geist_Mono({ variable: "--font-geist-mono", subsets: ["latin"] });

// 메타데이터 설정
export const metadata: Metadata = {
  title: "Create Next App",
  description: "Generated by create next app",
};

export default function ServerLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className={`${geistSans.variable} ${geistMono.variable} antialiased`}>
      <Topbar />
      {children}
    </div>
  );
}
