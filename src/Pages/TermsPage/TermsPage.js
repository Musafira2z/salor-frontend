import React, { useEffect, useState } from "react";
import NavigationBar from "../../Components/Sheard/NavigationBar/NavigationBar";
import { LanguageCodeEnum, usePageQuery, usePagePathsQuery } from "../../api";
import { useParams } from "react-router-dom";
import EditorJS from "@editorjs/editorjs";
import LinkTool from "@editorjs/link";
import List from "@editorjs/list";
import Header from "@editorjs/header";
import styled from "styled-components";
import NoteFoundPage from "../../Pages/404/NoteFoundPage";

const TermsPage = () => {
  const [cursor, setCursor] = useState("");
  const { slug } = useParams();

  const { data, loading } = usePagePathsQuery({
    variables: {
      after: cursor || "",
      first: 20,
      channel: "default",
      locale: LanguageCodeEnum.En,
    },
  });

  const [content, setContent] = useState("");
  const [title, setTitle] = useState("");

  useEffect(() => {
    let editor = { isReady: false };

    if (content?.blocks) {
      if (!editor.isReady) {
        const editor = new EditorJS({
          /**
           * Id of Element that should contain the Editor
           */
          holderId: "editorjs",
          readOnly: true,
          tools: {
            header: {
              class: Header,
              config: {
                levels: [2, 3, 4],
                defaultLevel: 1,
              },
            },
            list: {
              class: List,
              inlineToolbar: true,
              config: {
                defaultStyle: "unordered",
              },
            },
          },

          /**
           * Previously saved data that should be rendered
           */
          data: {
            time: 1550476186479,
            blocks: [
              ...content?.blocks?.map((item) => ({
                type: item?.type,
                data: {
                  text: item?.data?.text,
                  level: item?.data?.level,
                  items: item?.data?.items,
                  style: item?.data?.style || "",
                },
              })),
            ],
          },
        });
      }
    }
  }, [content]);

  useEffect(() => {
    data?.pages?.edges.forEach((item) => {
      if (item?.node?.slug === slug) {
        const parse = JSON.parse(item?.node?.content);
        setTitle(item?.node?.title);
        return setContent(parse);
      }
    });
  }, [data?.pages?.edges, slug]);

  //   useEffect(() => {
  //     if(data) {
  //       setContent(JSON.parse(data?.page?.content));
  //     }
  // }, [data]);

  return (
    <div>
      {title && (
        <>
          <NavigationBar />
          <div className="container  mx-auto  xl:w-6/12 lg:w-6/12  px-3 mt-5">
            <div className="px-3 bg-white rounded-xl">
              <h2 className="ce-header">{title}</h2>

              <div className="grid divide-y divide-neutral-200 w-full mx-auto ">
                <div id="editorjs"></div>
              </div>
            </div>
          </div>
        </>
      )}

      {
        !title && <NoteFoundPage />
      }


    </div>
  );
};

export default TermsPage;
