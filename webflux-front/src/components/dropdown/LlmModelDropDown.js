import React, {useState} from 'react';
import {ArrowDownIcon, ChevronDownIcon} from "lucide-react";
import {URL_SELECTION_LIST} from "../../utils/URLSelection";
import {changeUrlSelectionAction} from "../../reducer/UrlSelectionReducer";
import {useDispatch, useSelector} from "react-redux";
import {changeModelSelectionAction} from "../../reducer/LlmModelReducer";


const LlmModelDropDown = () => {

    const [showDropDown, setShowDropDown] = useState(false);

    const selectedModel = useSelector((state) => state.llmModel.selectedModel);
    const modelSelectionList = useSelector((state) => state.llmModel.modelSelectionList);

    const dispatch = useDispatch();
    return (
        <div className="relative inline-block text-left">
            <button type="button" onClick={() => {
                setShowDropDown(prevState => !prevState);
            }}
                    className="inline-flex justify-center items-center w-full px-4 py-2 text-sm font-medium dark:bg-[#1E2028] border border-gray-300 dark:border-gray-600 rounded-md hover:bg-gray-600 dark:hover:bg-[#252731] focus:outline-none">
                {selectedModel.displayName}
                <ChevronDownIcon/>
            </button>

            {showDropDown &&
                <div id="dropdown-menu"
                     className="absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white dark:bg-[#1E2028] ring-1 ring-black ring-opacity-5">
                    <div className="py-1 cursor-pointer" role="menu" aria-orientation="vertical">
                        {modelSelectionList.map((item, index) => (
                            <a
                                key={index}
                                className="block px-4 py-2 text-sm text-black dark:text-white hover:bg-gray-100 dark:hover:bg-[#252731]"
                                onClick={() => {
                                    dispatch(changeModelSelectionAction(item));
                                    setShowDropDown(false);
                                }}
                            >{item.displayName}</a>
                        ))}
                    </div>
                </div>
            }
        </div>
    );
};
export default LlmModelDropDown;