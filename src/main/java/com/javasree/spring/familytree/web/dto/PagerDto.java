package com.javasree.spring.familytree.web.dto;

public class PagerDto {

	private int buttonsToShow = 5;
	private int endPage;
	private int startPage;
	
	public PagerDto(int totalPages, int currentPage, int buttonsToShow){
		setButtonsToShow(buttonsToShow);
		
		int halfPagesToShow = getButtonsToShow() /2;
		
		if(totalPages <= getButtonsToShow()){
			setStartPage(1);
			setEndPage(totalPages);
		}
		else if (currentPage - halfPagesToShow <= 0) {
            setStartPage(1);
            setEndPage(getButtonsToShow());
        } else if (currentPage + halfPagesToShow == totalPages) {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(totalPages);
        } else if (currentPage + halfPagesToShow > totalPages) {
            setStartPage(totalPages - getButtonsToShow() + 1);
            setEndPage(totalPages);
        } else {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);
        }
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getButtonsToShow() {
		return buttonsToShow;
	}

	public void setButtonsToShow(int buttonsToShow) {
		if(buttonsToShow %2 !=0){
			this.buttonsToShow = buttonsToShow;
		}
		else{
			throw new IllegalArgumentException("Must not be an odd value!");
		}
		
	}
	
	@Override
    public String toString() {
        return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
    }
}
